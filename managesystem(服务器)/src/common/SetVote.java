package common;

import java.io.Serializable;

//序列化的对象包括基本数据类型，所有集合类以及其他许多东西，还有Class 对象
public class SetVote implements Serializable{
	
	String name;
	
	String item;
	
	String option1;
	String option2;
	String option3;
	String option4;
	
	int number1;
	int number2;
	int number3;
	int number4;
	
	String suggesstion;
	String vote_name;

	public String getVote_name() {
		return vote_name;
	}

	public void setVote_name(String vote_name) {
		this.vote_name = vote_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	public int getNumber3() {
		return number3;
	}

	public void setNumber3(int number3) {
		this.number3 = number3;
	}

	public int getNumber4() {
		return number4;
	}

	public void setNumber4(int number4) {
		this.number4 = number4;
	}

	public String getSuggesstion() {
		return suggesstion;
	}

	public void setSuggesstion(String suggesstion) {
		this.suggesstion = suggesstion;
	}
	
	

}
