package work3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class VocManager {

	private String userName;
	static Scanner scan = new Scanner(System.in);
	private Map<String,String> voc1 = new HashMap<>();
	private Map<String,String> voc2 = new HashMap<>();
	Random random = new Random();
	private List<Word> ox = new ArrayList<>();

	
	VocManager(String userName){
		this.userName = userName;
	}
	
	void addWord(Word word) {
		voc1.put(word.eng,word.kor);
		voc2.put(word.kor,word.eng);
		ox.add(word);
	}


	void makeVoc(String fileName) {
		
		try(Scanner scan = new Scanner(new File(fileName))){
			while(scan.hasNextLine()) {
				String str = scan.nextLine();
				String[] temp = str.split("\t");
				this.addWord(new Word(temp[0].trim(), temp[1].trim(),0,0));
				
				
			}
			System.out.println(userName+"의 단어장이 생성되었습니다.");
			this.menu();
			
		}catch(FileNotFoundException e) {
			System.out.println(userName+"의 단어장이 생성되지 않았습니다. \n파일명을 확인하세요.");
		}
		
	}

	void menu() {		
		int choice = 0;
		while(choice != 5) {	
			choice = 0;
			System.out.println("\n------"+userName+"의 영단어 퀴즈 -------");
			System.out.println("1) 주관식 퀴즈 2) 객관식 퀴즈 3) 오답노트 4) 단어검색 5)종료");			
			System.out.print("메뉴를 선택하세요 : ");
			try {
			choice = scan.nextInt();
			scan.nextLine();
			}catch(Exception e) {
				System.out.println("잘못된 입력입니다.");
				scan.nextLine();
			}
			
			System.out.println();

			switch (choice) {
			case 1 : 
				System.out.println("202211946 이재원");
				textQuiz();
				break;
			case 2 :
				System.out.println("202211946 이재원");
				numQuiz();
				break;
			case 3 : 
				System.out.println("202211946 이재원");
				Note();
				break;
			case 4 : 
				System.out.println("202211946 이재원");
				search();
				break;
			case 5 : 
				System.out.println(userName + "의 단어장 프로그램을 종료합니다.");
				break;
			default :
				System.out.println("잘못된 입력입니다.");
			
			}
			}
	}

	private void search() { //단어검색
		// TODO Auto-generated method stub
		System.out.println("------ 단어 검색 ------");
		System.out.print("검색할 단어를 입력하세요  : "); //영단어를 입력하지 않으면?
		int i=0;
		String sWord = scan.nextLine();
		sWord = sWord.trim();
		
		for(Word word : ox) {
			if(word!=null) {
				if(word.eng.equals(sWord)) {
					System.out.println(sWord+" 뜻 : "+word.kor);
					System.out.println("출제 횟수 :"+word.testNum+"\t오답 횟수 : "+word.oxNum);
					
					break;
				}
			else {
				i++;
			}
				if(i==ox.size()) {
					System.out.println("단어장에 등록된 단어가 아닙니다.");
				}
			}
		
		}
		}		
		
	

	private void Note() { //오답노트
		// TODO Auto-generated method stub
		int i=0;
		List<Word> OXlist = new ArrayList<>();
		Stream <Word> stream = OXlist.stream();
		for(Word word : ox) {
		if(word!=null) {
			if(word.oxNum>0) {
				OXlist.add(word);
				
				i=0;
			}else {
				i++;
			}
			
		}
		}
		if(i==ox.size()) {
			System.out.println("틀린 문제가 없습니다.");
		}else {
			OXlist.stream().sorted((o1,o2)->((o1.oxNum-o2.oxNum)*-1)).forEach(word ->System.out.println(word.eng+" 뜻 : "+ word.kor+"\n"+"출제횟수 : "+ word.testNum+"\t오답횟수 : "+word.oxNum+"\n"+"------------------------"));
			
		}
	}

	private void numQuiz() { //객관식 퀴즈
		// TODO Auto-generated method stub
		Set<String> keySet= voc1.keySet();
		List<String> keyList= new ArrayList<>(keySet);
		long after =0;
		long before=0;
		int correct=0;
	
		int size = keyList.size();
		int randomNum;
		String [] example = new String[4];
		Integer [] num = {0,1,2,3};
		
		if(size>0) {
			before = System.currentTimeMillis();
		for(int i=1; i<=10; i++) {
		 
		randomNum = new Random().nextInt(size);
		String randomKey = keyList.get(randomNum);
		String randomValue = voc1.get(randomKey);
		for(Word word : ox) {
			if(word!=null) {
				if(word.eng.equals(randomKey)) {
				word.testNum++;
				}
			}
			}
		
		int A =0;
		System.out.println("------ 객관식 퀴즈 "+i+"번------");
		System.out.println(randomKey+"의 뜻은 무엇일까요?");
		
		int randomExample = new Random().nextInt(size);
		String randomExKey = keyList.get(randomExample);
		String randomExValue = voc1.get(randomExKey);
		
		while((randomExValue.equals(randomValue))) {
			randomExample = new Random().nextInt(size);
			randomExKey = keyList.get(randomExample);
			randomExValue = voc1.get(randomExKey);
		}
		example[0]=randomValue;
		example[1]= randomExValue;
		for(Word word : ox) {
			if(word!=null) {
				if(word.kor.equals(example[1])) {
				word.testNum++;
				}
			}
			}
		
		while((randomExValue.equals(example[1]))||(randomExValue.equals(example[0]))) {
			randomExample = new Random().nextInt(size);
			randomExKey = keyList.get(randomExample);
			randomExValue = voc1.get(randomExKey);
		}
		
		example[2]=randomExValue;
		for(Word word : ox) {
			if(word!=null) {
				if(word.kor.equals(example[2])) {
				word.testNum++;
				}
			}
			}
		
		while((randomExValue.equals(example[2]))||(randomExValue.equals(example[1]))||(randomExValue.equals(example[0]))) {
			randomExample = new Random().nextInt(size);
			randomExKey = keyList.get(randomExample);
			randomExValue = voc1.get(randomExKey);
		}

		example[3]=randomExValue;
		for(Word word : ox) {
			if(word!=null) {
				if(word.kor.equals(example[3])) {
				word.testNum++;
				}
			}
			}
		
		
		List<Integer> list =Arrays.asList(num);
		Collections.shuffle(list);
		list.toArray(num);
		System.out.println("1) "+example[num[0]]);
		System.out.println("2) "+example[num[1]]);
		System.out.println("3) "+example[num[2]]);
		System.out.println("4) "+example[num[3]]);
		
		for(int j=0; j<4; j++) {
			if(num[j]==0) {
				A=j;
			}
		}
		
		A++;
		
		int Answer = 0;
		System.out.print("답을 입력하세요 : ");
		
	while(true) {
		try {
			Answer =scan.nextInt();
			break;
		}
		catch(InputMismatchException e){
			scan = new Scanner(System.in);
			System.out.print("답을 입력하세요 : ");
			break;
			
		}
	}
	
		
		if(Answer==A) {
			System.out.println("정답입니다.");
			correct++;
		}else {
			System.out.println("틀렸습니다. 정답은"+A+"번입니다.");
			for(Word word : ox) {
				if(word!=null) {
					if(word.eng.equals(randomKey)) {
					word.oxNum++;
					}
				}
			}
		}
		
		
			}
		after = System.currentTimeMillis();
		
		System.out.println(userName+"님 10문제 중 총 "+correct+"개 맞추셨고, 총"+((after-before)/1000)+"초 소요되었습니다." );
		}
	}

	private void textQuiz() { //주관식 퀴즈
		// TODO Auto-generated method stub
		Set<String> keySet= voc2.keySet();
		List<String> keyList= new ArrayList<>(keySet);
		List<String> Words = new ArrayList<>(10);
		
		long after =0;
		long before=0;
		int correct=0;
		int size = keyList.size();
		int randomNum;
		
		if(size>0) {
			before = System.currentTimeMillis();
		for(int i=1; i<=10; i++) {
		 
		randomNum = new Random().nextInt(size);
		String randomKey = keyList.get(randomNum);
		String randomValue = voc2.get(randomKey);
		
			if(!(Words.contains(randomKey))) {
		System.out.println("------ 주관식 퀴즈 "+i+"번------");
		int keyNum = random.nextInt();
		System.out.println(randomKey+"의 뜻을 가진 영어 단어는 무엇일까요?");
		System.out.print("답을 입력하세요 : ");
		String Answer = scan.nextLine();
		for(Word word : ox) {
			if(word!=null) {
				if(word.kor.equals(randomKey)) {
				word.testNum++;
				}
			}
			}
		Words.add(randomKey);
		if(((Answer.trim().equals(randomValue)))||(randomKey.equals(voc1.get(Answer.trim())))) {
			System.out.println("정답입니다.");
			correct++;
			
		}else {
			System.out.println("틀렸습니다. 정답은 "+randomValue+"입니다." );
			for(Word word : ox) {
				if(word!=null) {
					if(word.kor.equals(randomKey)) {
					word.oxNum++;
					}
				}
			}
		}
			}else
				i--;
			
				
		
		}
		 after = System.currentTimeMillis();
		}
		
		System.out.println(userName+"님 10문제 중 총 "+correct+"개 맞추셨고, 총"+((after-before)/1000)+"초 소요되었습니다." );
}

}
