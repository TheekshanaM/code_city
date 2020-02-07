package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ReturnStatement;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfReturns extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {
	private int qty = 0;

	@Override
	public void setResult(JMethodResult result) {
		result.setReturnQty(qty);

	}

	public boolean visit(ReturnStatement node) {
		qty++;
		return super.visit(node);
	}

	@Override
	public void setResult(JClassResult result) {
		result.setReturnQty(qty);

	}
}
