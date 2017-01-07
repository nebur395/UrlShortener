package urlshortener.aeternum.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urlshortener.common.domain.CountryRestriction;
import urlshortener.common.domain.DelayObject;
import urlshortener.common.repository.CountryResRepository;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

@Component
public class RequestQueue {

    @Autowired
    protected CountryResRepository countryResRepository;
    //Queues
    private DelayQueue spain = new DelayQueue();
    private DelayQueue italy = new DelayQueue();
    private DelayQueue ireland = new DelayQueue();
    private DelayQueue japan = new DelayQueue();

    /**
     * Returns true if the access is allowed.
     * Unless it is returned false.
     */
    public boolean canAccess(String country){
        boolean result;
        switch (country) {
            case "Spain":
                result = checkQueue(spain, country);
                break;
            case "Italy":
                result = checkQueue(italy, country);
                break;
            case "Ireland":
                result = checkQueue(ireland, country);
                break;
            case "Japan":
                result = checkQueue(japan, country);
                break;
            default: result = false;
        }
        return result;
    }

    private boolean checkQueue(DelayQueue q, String country) {
        CountryRestriction res = countryResRepository.findCountry(country);
        DelayObject d = new DelayObject(res.getTime()*1000);
        //if there aren't any request, it's allowed
        if (q.isEmpty()) return q.offer(d);
        else{
            try {
                //Remove elements witch has expired -> returns null if the time hasn't expired
                Object p = q.poll(1, TimeUnit.NANOSECONDS);
                while (p != null){
                    p = q.poll(1, TimeUnit.NANOSECONDS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //if it has passed less time and there are the max of request allowed, forbidding the redirection
            if (q.size() >= res.getRequest()) return false;
            else return q.offer(d);
        }
    }
}
