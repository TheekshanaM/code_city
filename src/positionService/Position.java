package positionService;

//import model.Position.Positions;
//import model.Position.generator;

public class Position {
final int defaultMargin = 1;
	
	public static class Positions{
		float x; 
		float y;
	}
	
	static class generator{
		int numberNodes;
		int dimension;
		float xReference;
		float yReference;
		int currentIndex;
		float maxWidth;
		float maxHeight;
	}
	
	generator NewGenerator(int numberNodes){
		generator obj = new generator();
		obj.numberNodes = numberNodes;
		obj.dimension = (int)(Math.ceil(Math.sqrt((float)(numberNodes))));
		
		return obj;
	}
	
	Positions GetBounds(generator g){
		Positions obj =  new Positions();
		obj.x = g.maxWidth + defaultMargin;
		obj.y = g.maxHeight + defaultMargin;
		return obj;
	}
	
	Positions NextPosition(generator g,float width, float height){
		g.currentIndex++;

		if (g.currentIndex > g.dimension && g.yReference+height >= g.maxWidth) {
			g.currentIndex = 0;
			g.yReference = 0;
			g.xReference = g.maxWidth + defaultMargin;
		}

		Positions position = new Positions();
		position.x = g.xReference + (width+defaultMargin)/2;
		position.y = g.yReference + (height+defaultMargin)/2;

		if( g.xReference+width > g.maxWidth ){
			g.maxWidth = g.xReference + width;
		}

		if( g.yReference+height > g.maxHeight ){
			g.maxHeight = g.yReference + height;
		}

		g.yReference += height + defaultMargin;

		return position;
	}
}
