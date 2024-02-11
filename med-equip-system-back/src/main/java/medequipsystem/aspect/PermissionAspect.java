package medequipsystem.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class PermissionAspect {
    /*
    @Before("execution(* medequipsystem.controller.ReservationController.createCustom()) || " +
            "execution(* medequipsystem.controller.ReservationController.createPredefined(medequipsystem.domain.Appointment, java.util.Set<medequipsystem.domain.ReservationItem>, medequipsystem.domain.Client))")
    public void getNameAdvice(){
        System.out.println("Executing Advice on getName()");
    }*/
}
