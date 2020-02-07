package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfVariables extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {
	private int qty = 0;

	@Override
	public void setResult(JMethodResult result) {
		result.setVariablesQty(qty);
	}

	public boolean visit(VariableDeclarationFragment node) {
		qty++;
		return super.visit(node);
	}

	@Override
	public void setResult(JClassResult result) {

		result.setVariablesQty(qty);

	}
}
