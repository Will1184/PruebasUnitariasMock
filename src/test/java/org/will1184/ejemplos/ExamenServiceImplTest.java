package org.will1184.ejemplos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.will1184.models.Examen;
import org.will1184.repository.ExamenRepository;
import org.will1184.repository.PreguntaRepository;
import org.will1184.service.ExamenServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

    @Mock
    ExamenRepository repository;

    @Mock
    PreguntaRepository preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp(){
        //MockitoAnnotations.openMocks(this);
        //repository = mock(examenRepository.class);
//        preguntaRepository = mock(preguntaRepository.class);
//        service = new ExamenServiceImpl(repository,preguntaRepository);
    }
    @Test
    void findExamenPorNombre(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        assertTrue(examen.isPresent());
        assertEquals(5L,examen.orElseThrow().getId());
        assertEquals("Matematicas", examen.get().getNombre());
    }

    @Test
    void findExamenPorNombreListaVacia(){
        List<Examen> datos = Collections.emptyList();
        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        assertFalse(examen.isPresent());
    }
    @Test
    void testPreguntasExamen(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong()))
                .thenReturn(Datos.PREGUNTAS);
        Examen examen= service.findExamenNombreConPreguntas("Matematicas");
        assertEquals(5,examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("integrales"));
    }

    @Test
    void testPreguntasExamenVerify(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong()))
                .thenReturn(Datos.PREGUNTAS);
        Examen examen= service.findExamenNombreConPreguntas("Matematicas");
        assertEquals(5,examen.getPreguntas().size());
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testNoExisteExamenVerify(){
        //given
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(preguntaRepository.findPreguntasPorExamenId(anyLong()))
                .thenReturn(Datos.PREGUNTAS);
        //when
        Examen examen= service.findExamenNombreConPreguntas("Matematicas");
        //then
        assertNull(examen);
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(5L);
    }

    @Test
    void testGuardarExamen(){
        //Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);
        when(repository.guardar(any(Examen.class))).then(new Answer<Examen>() {
            Long secuencia=8L;
            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen = invocation.getArgument(0);
                examen.setId(secuencia++);
                return examen;

            }
        });
        //when
        Examen examen = service.guardar(newExamen);
        assertNotNull(examen.getId());
        assertEquals(8L,examen.getNombre());
        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }

}