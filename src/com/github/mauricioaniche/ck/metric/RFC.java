package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.*;

import jcity.JClassResult;
import jcity.JMethodResult;

import java.util.HashSet;
import java.util.List;

public class RFC extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private HashSet<String> methodInvocations = new HashSet<String>();

	public boolean visit(MethodInvocation node) {
		IMethodBinding binding = node.resolveMethodBinding();
		count(node.getName()  + "/" + arguments(node.arguments()), binding);
		
		return super.visit(node);
	}

	private String arguments(List<?> arguments) {
		if(arguments==null || arguments.isEmpty()) return "0";
		return "" + arguments.size();
	}

	private void count(String methodName, IMethodBinding binding) {
		if(binding!=null) {
			String method = getMethodName(binding);
			methodInvocations.add(method);
		} else {
			methodInvocations.add(methodName);
		}
	}
	
	public boolean visit(SuperMethodInvocation node) {
		IMethodBinding binding = node.resolveMethodBinding();
		count(node.getName()  + "/" + arguments(node.arguments()), binding);
		
		return super.visit(node);
	}

	private String getMethodName(IMethodBinding binding) {
		
		String argumentList = "";
		ITypeBinding[] args = binding.getParameterTypes();
		for(ITypeBinding arg : args) {
			argumentList += arg.getName();
		}
		String method = binding.getDeclaringClass().getQualifiedName() + "." + binding.getName() + "/" + binding.getTypeArguments().length + "[" + argumentList + "]";
		return method;
	}
	
	@Override
	public void setResult(JClassResult result) {
		result.setRfc(methodInvocations.size());
	}

	@Override
	public void setResult(JMethodResult result) {
		result.setRfc(methodInvocations.size());
	}
}
