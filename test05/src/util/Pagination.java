package util;

public class Pagination {
	// 分页类，只需要得到四个数据,只需要get方法
	private int ye;
	private int maxYe;
	private int beginYe;
	private int endYe;
	private int begin;
	
	//要传过去数据计算各个属性的值，当前的ye数,所有信息的数目,每页显示的信息数量，一页显示几个页码
	public Pagination(int ye, int count, int numInPage, int numOfPage) {
		// 这是为了防止有人在url输入ye等于负数的情况，规避风险
		this.ye=ye;
		if (this.ye <= 1) {
			this.ye = 1;
		}
		// int maxYe = 0;
		// if (count % numInPage == 0) {
		// maxYe = count / numInPage;
		// } else {
		// maxYe = count / numInPage + 1;
		// }
		// 三目运算符，a?b:c a是Boolean类型，如果a是true，那么结果就是b，反之就是c，
		// 只能用于赋值，上面的等于：

		 maxYe = count % numInPage == 0 ? count / numInPage : count / numInPage + 1;
		if (this.ye >= maxYe) {
			this.ye = maxYe;
		}
		 beginYe = this.ye - numOfPage / 2;
		if (beginYe <= 1) {
			beginYe = 1;
		}
		 endYe = beginYe + numOfPage - 1;
		if (endYe >= maxYe) {
			endYe = maxYe;
			beginYe = endYe - numOfPage + 1;
		}
		if (beginYe <= 1) {
			beginYe = 1;
		}
		 begin = (this.ye - 1) * numInPage;
		

	}

	public int getYe() {
		return ye;
	}

	public int getMaxYe() {
		return maxYe;
	}

	public int getBeginYe() {
		return beginYe;
	}

	public int getEndYe() {
		return endYe;
	}
	public int getBegin() {
		return begin;
	}


}
