package edu.eci.arsw.api.primesrepo;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.service.PrimeService;
import edu.eci.arsw.api.primesrepo.service.PrimeServiceException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@RestController
public class PrimesController {

    @Autowired
    PrimeService primeService;


    @RequestMapping( value = "/primes", method = GET )
    public ResponseEntity<?> getPrimes() {
        List<FoundPrime> data;
        try {
            data = primeService.getFoundPrimes();
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (PrimeServiceException e) {
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping( path = "primes/{number}", method = GET )
    public ResponseEntity<?> getPrimeNumber(@PathVariable("number") String priNumber){
        FoundPrime data = null;
        try {
            data = primeService.getPrime(priNumber);
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (PrimeServiceException e) {
            return new ResponseEntity<>("ERROR",HttpStatus.NOT_FOUND);
        }
    }
    //curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://localhost:8080/primes/create -d "{"""user""":"""wilson""","""prime""":"""69"""}"
    @RequestMapping(path = "/create", method = POST)
    public ResponseEntity<?> addNewPrime(@RequestBody FoundPrime f){
        try {
            primeService.addFoundPrime(f);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PrimeServiceException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
