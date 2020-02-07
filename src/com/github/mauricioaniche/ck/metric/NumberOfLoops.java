package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.*;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfLoops extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private int qty = 0;

	public boolean visit(EnhancedForStatement node) {
		qty++;
		return super.visit(node);
	}

	public boolean visit(DoStatement node) {
		qty++;
		return super.visit(node);
	}

	public boolean visit(WhileStatement node) {
		qty++;
		return super.visit(node);
	}

	public boolean visit(ForStatement node) {
		qty++;
		return super.visit(node);
	}

	@Override
	public void setResult(JMethodResult result) {
		result.setLoopQty(qty);

	}

	@Override
	public void setResult(JClassResult result) {
		result.setLoopQty(qty);
	}
}
