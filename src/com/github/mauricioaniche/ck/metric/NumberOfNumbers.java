package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.NumberLiteral;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfNumbers extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private int qty = 0;

	@Override
	public boolean visit(NumberLiteral node) {
		qty++;
		return super.visit(node);
	}
	@Override
	public void setResult(JMethodResult result) {
		result.setNumbersQty(qty);

	}

	@Override
	public void setResult(JClassResult result) {
		result.setNumbersQty(qty);
	}
}
