package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@Service
public class PrimeServiceStub implements PrimeService {
    List<FoundPrime> totalFoundPrime;
    public PrimeServiceStub() {
        //Initialization of the values
        totalFoundPrime = new CopyOnWriteArrayList<>();

        FoundPrime fp1 = new FoundPrime();
        FoundPrime fp2 = new FoundPrime();
        FoundPrime fp3 = new FoundPrime();
        FoundPrime fpn = new FoundPrime();

        fp1.setPrime("1");
        fp1.setUser("luis");
        fp2.setPrime("2");
        fp2.setUser("alejandro");
        fp3.setPrime("3");
        fp3.setUser("carlos");
        fpn.setPrime("32416190071");
        fpn.setUser("john");

        totalFoundPrime.add(fp1);
        totalFoundPrime.add(fp2);
        totalFoundPrime.add(fp3);
        totalFoundPrime.add(fpn);
    }


    @Override
    public void addFoundPrime( FoundPrime foundPrime ) throws PrimeServiceException {
        for(FoundPrime f : totalFoundPrime){
            if(foundPrime.getPrime().equals(f.getPrime())){
                throw new PrimeServiceException("Primo Existente");
            }
        }
        totalFoundPrime.add(foundPrime);
    }

    @Override
    public List<FoundPrime> getFoundPrimes() {
        return totalFoundPrime;
    }

    @Override
    public FoundPrime getPrime( String prime ) throws PrimeServiceException {
        for (FoundPrime f : totalFoundPrime){
            if (f.getPrime().equals(prime)){
                return f;
            }
        }
        throw new PrimeServiceException("Primo NO Existente");
    }

}
