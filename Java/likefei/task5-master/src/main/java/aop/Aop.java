package aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
//实现aop功能类
public class Aop {
    //日志输出
    private final Logger logger = Logger.getLogger(Aop.class);
    //对StudentController下的所有方法切入
    @Pointcut("execution(* action..*.*(..))")
    //定义一个空方法
    public  void controller(){
    }
//    @Pointcut("execution(* mapper.StudentMapper*.*(..))")
//    public void mapper(){
//
//    }
    //对该方法环绕增强
    @Around(value = "controller()")
    //控制器每个方法执行消耗时间
    // 在环绕通知中需要明确调用ProceedingJoinPoint的proceed()方法来执行被代理的方法。如果忘记这样做就会导致通知被执行了，但目标方法没有被执行。
    //注意：环绕通知的方法需要返回目标方法执行之后的结果，即调用 joinPoint.proceed();的返回值，否则会出现空指针异常。
    public Object Time(ProceedingJoinPoint proceedingJoinPoint){
        // proceedingJoinPoint封装了连接点的详细信息
        // proceed，执行目标方法 method.invoke
        Object object = null;
        Object[] args = proceedingJoinPoint.getArgs();
        long start = System.currentTimeMillis();
        try {
            // method.invoke
            // 目标方法执行完成后会有返回值，这个返回值一定return出去
            object = proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
           logger.error("arounderror",throwable);
        }
        long end = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();
        String methodname = methodSignature.getDeclaringTypeName()+"."+methodSignature.getName();
        logger.info("method:"+methodname+"time:"+(end-start));
        return  object;
    }
}