package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.InfixExpression;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfComparisons extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private int qty = 0;

	@Override
	public boolean visit(InfixExpression node) {

		if(node.getOperator() == InfixExpression.Operator.EQUALS)
			qty++;

		if(node.getOperator() == InfixExpression.Operator.NOT_EQUALS)
			qty++;

		return super.visit(node);

	}

	@Override
	public void setResult(JMethodResult result) {
		result.setComparisonsQty(qty);
	}

	@Override
	public void setResult(JClassResult result) {
		result.setComparisonsQty(qty);
	}
}
