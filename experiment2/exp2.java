/*An online examination platform wants to build a flexible proctoring controller for different types of exams conducted across multiple departments.
Some exams require only identity verification, while others require identity verification, environment checking and continuous behaviour monitoring. Each of these activities can be performed using different techniques such as human-assisted verification, AI-based verification or biometric verification. The platform must be able to construct a proctoring pipeline for a specific exam at runtime by selecting only the required monitoring components and arranging them in the execution flow.

Design an object-oriented solution in which each monitoring activity is represented by its own small abstraction, no component is forced to implement operations that it does not logically support, and the proctoring controller depends only on abstractions. It must be possible to replace or add a monitoring technique (for example, switching behaviour monitoring from AI-based to human-assisted) without changing the controller logic, and different monitoring strategies must be executed through runtime polymorphism within the same exam session*/


import java.util.ArrayList;
import java.util.List;
interface ProctoringStep 
{
    void execute();
}

class AIIdentityVerification implements ProctoringStep 
{
    public void execute() 
    { System.out.println("Verifying Identity: AI Scanning..."); }
}

class BiometricVerification implements ProctoringStep 
{
    public void execute() 
    { System.out.println("Verifying Identity: Fingerprint Scan..."); }
}

class AIEnvironmentCheck implements ProctoringStep 
{
    public void execute() 
    { System.out.println("Checking Environment: AI Room Scan..."); }
}

class HumanBehaviorMonitoring implements ProctoringStep 
{
    public void execute() 
    { System.out.println("Monitoring Behavior: Human Proctor watching..."); }
}

class ProctoringController {
    private final List<ProctoringStep> pipeline = new ArrayList<>();
    public void addStep(ProctoringStep step)
     {
        pipeline.add(step);
     }

    public void startExam() 
    {
        System.out.println("--- Starting Proctoring Session ---");
        for (ProctoringStep step : pipeline) 
          step.execute();
         System.out.println("--- Session Ready ---\n");
    }
}

public class exp2 
{
    public static void main(String[] args) 
    {       
        ProctoringController simpleExam = new ProctoringController();
        simpleExam.addStep(new AIIdentityVerification());
        
        System.out.println("Exam 1 (Simple):");
        simpleExam.startExam();

        ProctoringController strictExam = new ProctoringController();
        strictExam.addStep(new BiometricVerification());
        strictExam.addStep(new AIEnvironmentCheck());
        strictExam.addStep(new HumanBehaviorMonitoring());
        
        System.out.println("Exam 2 (Strict):");
        strictExam.startExam();
    }
}