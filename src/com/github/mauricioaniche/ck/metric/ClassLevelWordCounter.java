package com.github.mauricioaniche.ck.metric;

import jcity.JClassResult;
import jcity.util.WordCounter;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassLevelWordCounter extends ASTVisitor implements ClassLevelMetric {

	private int qtyOfUniqueWords;

	private boolean visitedTypeAlready = false;

	public boolean visit(TypeDeclaration node) {

		if(visitedTypeAlready) return super.visit(node);

		String classSourceCode = node.toString();
		this.qtyOfUniqueWords = WordCounter.wordsIn(classSourceCode).size();

		visitedTypeAlready = true;
		return super.visit(node);

	}

	@Override
	public void setResult(JClassResult result) {
		result.setUniqueWordsQty(qtyOfUniqueWords);

	}
}
