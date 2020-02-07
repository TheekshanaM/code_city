package dependency.dependencies;

import dependency.enums.DependencyType;

import dependency.util.DCLUtil;

@SuppressWarnings("serial")
public class DeclareDependency extends HandleDependency {

	public DeclareDependency(String classNameA, String classNameB, Integer lineNumberA, Integer offset, Integer length) {
		super(classNameA, classNameB, lineNumberA, offset, length);
	}
	
	@Override
	public DependencyType getDependencyType() {
		return DependencyType.DECLARE;
	}
	
	@Override
	public String toShortString() {
		return "The declaration of " + DCLUtil.getSimpleClassName(this.classNameB) + " is disallowed for this location w.r.t. the architecture";
	}
}