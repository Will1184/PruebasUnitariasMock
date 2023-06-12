package org.will1184.repository;

import org.will1184.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositoryOther implements ExamenRepository{
    @Override
    public Examen guardar(Examen examen) {
        return null;
    }

    @Override
    public List<Examen> findAll() {
        try {
            System.out.println("ExamenRepositoryOther");
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
}
