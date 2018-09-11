package util;

public class Pagination {
	// ��ҳ�ֻ࣬��Ҫ�õ��ĸ�����,ֻ��Ҫget����
	private int ye;
	private int maxYe;
	private int beginYe;
	private int endYe;
	private int begin;
	
	//Ҫ����ȥ���ݼ���������Ե�ֵ����ǰ��ye��,������Ϣ����Ŀ,ÿҳ��ʾ����Ϣ������һҳ��ʾ����ҳ��
	public Pagination(int ye, int count, int numInPage, int numOfPage) {
		// ����Ϊ�˷�ֹ������url����ye���ڸ������������ܷ���
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
		// ��Ŀ�������a?b:c a��Boolean���ͣ����a��true����ô�������b����֮����c��
		// ֻ�����ڸ�ֵ������ĵ��ڣ�

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
