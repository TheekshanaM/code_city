package com.github.mauricioaniche.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;

import jcity.JClassResult;
import jcity.JMethodResult;

public class NumberOfMaxNestedBlock extends ASTVisitor implements ClassLevelMetric, MethodLevelMetric {

	private int current = 0;
	private int max = 0;

	@Override
	public boolean visit(Block node) {
		current++;
		max = Math.max(current, max);

		return super.visit(node);
	}

	@Override
	public void endVisit(Block node) {
		current--;
	}

	@Override
	public void setResult(JMethodResult result) {
		result.setMaxNestedBlocks(max - 1); // -1 because the method block is considered a block.

	}

	@Override
	public void setResult(JClassResult result) {
		result.setMaxNestedBlocks(max - 1);
	}
}
