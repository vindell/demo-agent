package bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("this is an perform monitor agent.");

        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {

			@Override
			public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader,
					JavaModule module) {
				return builder
                        .method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
                        .intercept(MethodDelegation.to(TimeInterceptor.class)); // 委托
			}
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
          
			@Override
			public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
					boolean loaded, DynamicType dynamicType) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
					boolean loaded) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded,
					Throwable throwable) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
				// TODO Auto-generated method stub
				
			}
        };

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("com.example.demo")) // 指定需要拦截的类
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }
}