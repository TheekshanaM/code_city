package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TryStatement;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfTryCatches extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private int qty = 0;

	public boolean visit(TryStatement node) {
		qty++;
		return true;
	}

	@Override
	public void setResult(JMethodResult result) {
		result.setTryCatchQty(qty);

	}

	@Override
	public void setResult(JClassResult result) {
		result.setTryCatchQty(qty);
	}
}
