package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.StringLiteral;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfStringLiterals extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private int qty = 0;

	public boolean visit(StringLiteral node) {
		qty++;
		return super.visit(node);
	}
	@Override
	public void setResult(JMethodResult result) {
		result.setStringLiteralsQty(qty);

	}

	@Override
	public void setResult(JClassResult result) {
		result.setStringLiteralsQty(qty);
	}
}
