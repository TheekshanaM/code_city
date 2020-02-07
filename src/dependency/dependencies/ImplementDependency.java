package dependency.dependencies;

import dependency.enums.DependencyType;

@SuppressWarnings("serial")
public class ImplementDependency extends DeriveDependency {
	public ImplementDependency(String classNameA, String classNameB, Integer lineNumberA, Integer offset, Integer length) {
		super(classNameA,classNameB,lineNumberA, offset, length);
	}
		
	@Override
	public DependencyType getDependencyType() {
		return DependencyType.IMPLEMENT;
	}
}