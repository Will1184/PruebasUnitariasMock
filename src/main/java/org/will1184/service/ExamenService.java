package org.will1184.service;

import org.will1184.models.Examen;

import java.util.Optional;

public interface ExamenService {
    Optional<Examen> findExamenPorNombre(String nombre);
    Examen findExamenNombreConPreguntas(String nombre);
    Examen guardar(Examen examen);
}
