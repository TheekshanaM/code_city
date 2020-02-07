package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfAssignments extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private int qty = 0;

	@Override
	public boolean visit(Assignment node) {
		qty++;
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		if(node.getInitializer()!=null)
			qty++;

		return super.visit(node);
	}

	@Override
	public void setResult(JMethodResult result) {
		result.setAssignmentsQty(qty);

	}

	@Override
	public void setResult(JClassResult result) {
		result.setAssignmentsQty(qty);
	}
}
