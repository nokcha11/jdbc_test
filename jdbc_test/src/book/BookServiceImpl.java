package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BookServiceImpl implements BookService {
	
	BookDAO bookDAO = new BookDAO();
	Scanner sc = new Scanner(System.in);

	@Override
	public void startProgram() {
		
		while (true) {
			
			int choice = printMenu();
			
			switch (choice) {
			
			case 1 :
				System.out.println("1. 도서 정보 등록");
				insertBook();
				break;
			case 2 :
				System.out.println(" 2. 도서 정보 수정");
				updateBook();
				break;
			case 3 :
				System.out.println("3. 도서 정보 삭제");
				deleteBook();
				break;
			case 4 :
				System.out.println("4. 도서 정보 출력(이름)");
				printBook();
				break;
			case 5 :
				System.out.println("5. 도서 전체 정보 출력");
				printAllBooks();
				break;
			case 6 :
				System.out.println("프로그램 종료 ~!!");
				
				break;
			default:
				// "잘못된 숫자가 입력됩. 1~6 사이의 숫자 입력 가능"
				break;
			}
			
		}
		
	}

	@Override
	public int printMenu() {
		System.out.println( "===== 도서 관리 프로그램 =====");
		 System.out.println("1. 도서 정보 등록");
		 System.out.println("2. 도서 정보 수정");
		 System.out.println("3. 도서 정보 삭제");
		 System.out.println("4. 도서 정보 출력(이름)");
		 System.out.println("5. 도서 전체 정보 출력");
		 System.out.println("6. 프로그램 종료");
		 System.out.println("[선택] : ");
		 
		 int choice = sc.nextInt();

	
		 return choice;
		
	}
	// 도서정보 등록
	public void insertBook() {
		System.out.println("도서명을 입력하세요>>>>");
		sc.nextLine();
		String title = sc.nextLine();
		
		System.out.println("도서가격을 입력하세요>>>>");
		int price = sc.nextInt();
		
		System.out.println("저자를 입력하세용>>>>>");
		sc.nextLine();
		String author = sc.nextLine();
		
		System.out.println("출판사를 입력하세요>>>>>");
		String publisher = sc.nextLine();
		
		System.out.println("출판년도를 입력하세요>>>>>");
		String pubYear = sc.nextLine();
		
		System.out.println("ISBN 입력하세요>>>>>");
		String isbn = sc.nextLine();
		
		System.out.println("총페이지수를 입력하세요>>>>>");
		int page = sc.nextInt();
		
		BookInfo bookInfo = new BookInfo();
		bookInfo.setTitle(title);
		bookInfo.setPrice(price);
		bookInfo.setAuthor(author);
		bookInfo.setPublisher(publisher);
		bookInfo.setPubYear(pubYear);
		bookInfo.setIsbn(isbn);
		bookInfo.setPage(page);
		
		int resultChk = 0;
		resultChk = bookDAO.insertBook(bookInfo);
		if(resultChk >0 ) {
			System.out.println("도서가 등록되었습니다.");
		} else {
			System.out.println("도서 등록에 실패하였습니다.");
		}
	}
	//도서정보 전체 출력
	public void printAllBooks() {
		List<HashMap<String, Object>> bookList = new ArrayList();
		bookList = bookDAO.printAllBooks();
		System.out.println("도서명\t저자\t출판사\t\t생성일자");
		for (int i = 0; i < bookList.size(); i++) {
			System.out.print(bookList.get(i).get("book_title")+"\t");
			System.out.print(bookList.get(i).get("book_author")+"\t");
			System.out.print(bookList.get(i).get("book_publisher")+"\t\t");
			System.out.println(bookList.get(i).get("create_date")+"\t");
		}
		
	}
	// 도서정보 출력(이름)
	public void printBook() {
		
		List<HashMap<String, Object>> bookList = new ArrayList();
		System.out.println("검색할 도서명을 입력하세요>>>>>>>");
		sc.nextLine();
		String title = sc.nextLine();
		
		bookList = bookDAO.printSearchBooks(title);
		System.out.println("도서명\t저자\t출판사\t\t생성일자");
		for (int i = 0; i < bookList.size(); i++) {
			System.out.print(bookList.get(i).get("book_title")+"\t");
			System.out.print(bookList.get(i).get("book_author")+"\t");
			System.out.print(bookList.get(i).get("book_publisher")+"\t\t");
			System.out.println(bookList.get(i).get("create_date")+"\t");
			}
		}
	
	// 도서정보 삭제
	public void deleteBook() {
		System.out.println("도서명을 입력하세요>>>>");
		sc.nextLine();
		String title = sc.nextLine(); // 도서명을 변수로 받아서
		
		
		int resultChk = 0;
		resultChk = bookDAO.deletetBook(title); // 할당받은 변수넣어준다. DAO에서는 다른 변수명으로해도 무방
		if(resultChk >0 ) {
			System.out.println("도서가 삭제되었습니다.");
		} else {
			System.out.println("도서 삭제에 실패하였습니다.");
		}
	
	}
	
	// 도서정보 수정
	public void updateBook() {
		System.out.println("도서명을 입력하세요>>>>>>>");
		
		sc.nextLine();
		String title = sc.nextLine();
		
		List<HashMap<String, Object>> bookList = new ArrayList();
		// 서치할 printSearchBooks의 도서명을 검색하기때문에 
		bookList = bookDAO.printSearchBooks(title);
		System.out.println("도서명ID\t도서명\t저자\t출판사\t\t생성일자");
		// Imp의 대문자 소문자와 맞아야 한다.
		for (int i = 0; i < bookList.size(); i++) {
			System.out.print(bookList.get(i).get("book_id")+"\t");
			System.out.print(bookList.get(i).get("book_title")+"\t");
			System.out.print(bookList.get(i).get("book_author")+"\t");
			System.out.print(bookList.get(i).get("book_publisher")+"\t\t");
			System.out.println(bookList.get(i).get("create_date")+"\t");
		}
		System.out.println("수정할 도서의 순번을 입력하세요>>>>");
		// 몇번째 순번 입력받을지
		int num = sc.nextInt();
		// pk값인 book_id값으로 가져온다.
		// 인덱스번호를 맞추기위해 -1로 맞춘다.
		// <String, Object>>로 객체로 받았기 떄문에 타입변환을 해야한다.
		int bookID = Integer.parseInt(bookList.get(num-1).get("book_id").toString());
		System.out.println("변경될 도서명을 입력하세요>>>>>");
		sc.nextLine();
		String updateTitle = sc.nextLine();
		
		int resultChk = 0;
		resultChk = bookDAO.updateBook(bookID, updateTitle);
		if(resultChk >0 ) {
			System.out.println("도서가 수정되었습니다.");
		} else {
			System.out.println("도서 수정에 실패하였습니다.");
		}
	}
 }
