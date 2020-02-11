package puzzle8;

public class Puzzle8 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[]finalArray = {1,2,3,4,0,5,6,7,8};
		int [] puzzleArray = {5,6,3,4,0,8,7,1,2};
		new HuristicSolution().initiateSolution(finalArray,puzzleArray);
	}
}

class HuristicSolution{

	int goalArray[];
	int intermediateArr[];

	int huristicLevel = 0;
	Moves lastMove = Moves.NONE;
	Moves tempLastMove = Moves.NONE;
	Moves[]possibleMoves;

	void solvePuzzle(int [] arr) {
		
		int level = 0;
		while(!isArraysMatching(goalArray, arr)) {
			
			level++;
			Moves moves[];
			
				moves = new Moves[getPossibleMoves(arr,tempLastMove).length];
				for(int i=0;i<getPossibleMoves(arr,tempLastMove).length;i++) {
					if(!getPossibleMoves(arr,tempLastMove)[i].equals(tempLastMove)) {
						moves[i]=getPossibleMoves(arr,tempLastMove)[i];
					}
				}
			
			int huristicValue[] = new int[moves.length];
			
			for(int i=0;i<moves.length;i++) {
				int temp[] = new int[arr.length];
				for(int j = 0;j<temp.length;j++) {
					temp[j]=arr[j];
				}
				System.out.println("TEMP");
				if(moves[i].equals(Moves.DOWN)) {
					temp = moveTileDown(getBlankPosition(temp), temp);
					tempLastMove = Moves.DOWN;
				}else if(moves[i].equals(Moves.UP)) {
					temp = moveTileUp(getBlankPosition(temp), temp);
					tempLastMove = Moves.UP;

				}else if(moves[i].equals(Moves.RIGHT)) {
					temp = moveTileRight(getBlankPosition(temp), temp);
					tempLastMove = Moves.RIGHT;

				}else if(moves[i].equals(Moves.LEFT)) {
					temp = moveTileLeft(getBlankPosition(temp), temp);
					tempLastMove = Moves.LEFT;

				}
				huristicValue[i]=getHuristicValue(temp);
			}
			
			int minHuresticIndex = getMinHuristicArrIndex(huristicValue);
			Moves move =moves[minHuresticIndex];
			if(move.equals(Moves.DOWN)) {
				System.out.println("real Move");
				 moveTileDown(getBlankPosition(arr), arr);
				 lastMove = move;
				 tempLastMove = move;
			}else if(move.equals(Moves.UP)) {
				System.out.println("real Move");
				 moveTileUp(getBlankPosition(arr), arr);
				 lastMove = move;
				 tempLastMove = move;
			}else if(move.equals(Moves.RIGHT)) {
				System.out.println("real Move");
				moveTileRight(getBlankPosition(arr), arr);
				 lastMove = move;
				 tempLastMove = move;
			}else if(move.equals(Moves.LEFT)) {
				System.out.println("real Move");
				 moveTileLeft(getBlankPosition(arr), arr);
				 lastMove = move;
				 tempLastMove = move;
			}
			
			System.out.println("minHuristic move "+move.value+" level "+level);
			
		}
		
		
		
	}
	
	int getMinHuristicArrIndex(int[]huristicArr) {
		int min = 10;
		int minIndex = -1;
		for(int i=0;i<huristicArr.length;i++) {
			if(huristicArr[i]<min) {
				min=huristicArr[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	int [] moveTileLeft(int blankIndex,int[]arr) {
		System.out.println("*********moving left*********");
		return moveTile(blankIndex, blankIndex-1, arr);
	}
	int [] moveTileRight(int blankIndex,int[]arr) {
		System.out.println("*********moving right*********");
		return moveTile(blankIndex, blankIndex+1, arr);
	}
	int [] moveTileUp(int blankIndex,int[]arr) {
		System.out.println("*********moving up*********");
		return moveTile(blankIndex, blankIndex-3, arr);
	}
	int [] moveTileDown(int blankIndex,int[]arr) {
		System.out.println("*********moving down*********");
		return moveTile(blankIndex, blankIndex+3, arr);
	}
	
	int [] moveTile(int blankIndex,int tileIndex,int[]arr) {
		int temp = arr[blankIndex];
		arr[blankIndex] = arr[tileIndex];
		arr[tileIndex]=temp;
		printArray(arr);
		return arr;
	}

	Moves[] getPossibleMoves(int[] arr,Moves lastMove) {
		Moves [] moves;
		int blankPosition = getBlankPosition(arr)+1;
		if(blankPosition<=3) {
			if(blankPosition%3==0) {
				moves = new Moves[2];
				moves[0] = Moves.DOWN;
				moves[1] = Moves.LEFT;
			}else if(blankPosition%3==1){
				moves = new Moves[2];
				moves[0] = Moves.DOWN;
				moves[1] = Moves.RIGHT;
			}else {
				moves = new Moves[3];
				moves[0] = Moves.DOWN;
				moves[1] = Moves.RIGHT;
				moves[2] = Moves.LEFT;
			}
		}else if((getBlankPosition(arr)+1)<=6) {
			if(blankPosition%3==0) {
				moves = new Moves[3];
				moves[0] = Moves.DOWN;
				moves[1] = Moves.LEFT;
				moves[2] = Moves.UP;
			}else if(blankPosition%3==1){
				moves = new Moves[3];
				moves[0] = Moves.DOWN;
				moves[1] = Moves.RIGHT;
				moves[2] = Moves.UP;
			}else {
				moves = new Moves[4];
				moves[0] = Moves.DOWN;
				moves[1] = Moves.RIGHT;
				moves[2] = Moves.LEFT;
				moves[3] = Moves.UP;
			}
		}else {
			if(blankPosition%3==0) {
				moves = new Moves[2];
				moves[0] = Moves.UP;
				moves[1] = Moves.LEFT;
			}else if(blankPosition%3==1){
				moves = new Moves[2];
				moves[0] = Moves.UP;
				moves[1] = Moves.RIGHT;
			}else {
				moves = new Moves[3];
				moves[0] = Moves.UP;
				moves[1] = Moves.RIGHT;
				moves[2] = Moves.LEFT;
			}
		}
		if(lastMove.equals(Moves.NONE)) {
			return moves;
		
		}else {
			Moves[] newMoves = new Moves[moves.length-1];
			for(int i=0,j=0;i<moves.length;i++) {
				if(moves[i].equals(lastMove)) {
					continue;
				}else {
					newMoves[j]=moves[i];
					j++;
				}
			}
			return newMoves;
		}
	}

	int getBlankPosition(int [] arr){
		for(int i = 0;i<arr.length;i++) {
			if(arr[i] == 0) {
				return i;
			}
		}
		return -1;//check when this is possible
	}

	int getHuristicValue(int[]arr) {
		int h = 0;
		for(int i=0;i<this.goalArray.length;i++) {
			if(this.goalArray[i]!=arr[i]) {
				h++;
			}
		}
		return h;
	}

	void initiateSolution(int[]finalArr,int[]initalArr){
		this.goalArray = finalArr;
		this.intermediateArr = initalArr;
		if(isArraysMatching(finalArr, initalArr)) {
			System.out.print("matched");
		}else {
			solvePuzzle(initalArr);
		}
	}



	boolean isArraysMatching(int []lhs,int[]rhs) {
		if(lhs!=null&&rhs!=null&&lhs.length==rhs.length) {
			for(int i=0;i<lhs.length;i++) {
				if(lhs[i]!=rhs[i]) {
					return false;
				}
			}
		}
		return true;
	}

	void printArray(int[]array){
		for(int i=0;i<array.length;i++) {
			if((i+1)%3==0) {
				if(array[i]==0) {
					System.out.println(" ");
				}else {
					System.out.println(array[i]);
				}
			}else {
				if(array[i]==0) {
					System.out.print(" ");
				}else {
					System.out.print(array[i]);
				}
			}
		}
	}
}

enum Moves{
	NONE("none"),UP("up"),DOWN("down"),RIGHT("right"),LEFT("left");
	String value;  
	Moves(String value){  
		this.value=value;  
	}
}
