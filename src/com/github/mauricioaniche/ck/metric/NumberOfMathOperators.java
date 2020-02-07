package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.InfixExpression;

import jcity.JClassResult;
import jcity.JMethodResult;

import java.util.Arrays;

public class NumberOfMathOperators extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private int qty = 0;

	static InfixExpression.Operator[] operators = new InfixExpression.Operator[] {
		InfixExpression.Operator.TIMES, InfixExpression.Operator.DIVIDE,
			InfixExpression.Operator.REMAINDER,InfixExpression.Operator.PLUS,
			InfixExpression.Operator.MINUS,InfixExpression.Operator.LEFT_SHIFT,
			InfixExpression.Operator.RIGHT_SHIFT_SIGNED,InfixExpression.Operator.RIGHT_SHIFT_UNSIGNED
	};

	@Override
	public boolean visit(InfixExpression node) {

		if( Arrays.stream(operators).anyMatch(node.getOperator()::equals))
			qty++;

		return super.visit(node);

	}

	@Override
	public void setResult(JMethodResult result) {
		result.setMathOperationsQty(qty);
	}

	@Override
	public void setResult(JClassResult result) {
		result.setMathOperationsQty(qty);
	}
}
