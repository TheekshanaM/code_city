package com.github.mauricioaniche.ck.metric;

import jcity.JMethodResult;
import jcity.util.WordCounter;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodLevelWordCounter extends ASTVisitor implements MethodLevelMetric {
	private int qtyOfUniqueWords;

	public boolean visit(MethodDeclaration node) {
		String methodSourceCode = node.toString();

		this.qtyOfUniqueWords = WordCounter.wordsIn(methodSourceCode).size();

		return super.visit(node);
	}


	@Override
	public void setResult(JMethodResult result) {
		result.setUniqueWordsQty(qtyOfUniqueWords);
	}
}
