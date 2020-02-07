package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfParenthesis extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private int qty = 0;

	public boolean visit(ParenthesizedExpression node) {
		qty++;
		return true;
	}

	@Override
	public void setResult(JMethodResult result) {
		result.setParenthesizedExpsQty(qty);

	}

	@Override
	public void setResult(JClassResult result) {
		result.setParenthesizedExpsQty(qty);
	}
}
