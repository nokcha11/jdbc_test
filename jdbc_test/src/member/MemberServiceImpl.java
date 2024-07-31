package member;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class MemberServiceImpl implements MemberService {

	MemberDAO mDAO = new MemberDAO();
	Scanner sc = new Scanner(System.in);
	
	@Override
	public void startProgram() {
	
	int count = 0;
	
	while (true) {
		
			int choice = printMenu();
			
			switch (choice) {
			
			case 1 :
				displayMsg("1. 회원 정보 등록");
				insertMember();
				break;
			case 2 :
				displayMsg(" 2. 회원 정보 수정");
				updateMember();
				break;
			case 3 :
				displayMsg("3. 회원 정보 삭제");
				deleteMember();
				break;
			case 4 :
				displayMsg("4. 회원 정보 출력(이름)");
				printSearchMember();
				break;
			case 5 :
				displayMsg("5. 회원 전체 정보 출력");
				printAllMembers();
				break;
			case 6 :
				displayMsg("프로그램 종료 ~!!");
				count++;
				break;
			default:
				// "잘못된 숫자가 입력됩. 1~6 사이의 숫자 입력 가능"
				break;
			}
			if (count == 1) {
				break;
			}
		}
	
	}
	
	public int printMenu() {
		displayMsg( "===== 회원 관리 프로그램 =====");
		displayMsg("1. 회원 정보 등록");
		displayMsg("2. 회원 정보 수정");
		displayMsg("3. 회원 정보 삭제");
		displayMsg("4. 회원 정보 출력(이름)");
		displayMsg("5. 회원 전체 정보 출력");
		System.out.println("6. 프로그램 종료");
		System.out.println("[선택] : ");
		
		int choice = sc.nextInt();
		
		
		return choice;
	}
	
	// 메세지 출력용
		public void displayMsg(String msg) {
			System.out.println(msg);
		}

		// 1. 회원 등록
		public void insertMember() {
			Member member = new Member();
			
			System.out.println("회원 아이디를 입력해 주세요 : ");
			String memberId = sc.next();
			
			System.out.println("회원 비밀번호 : ");
			String  memberPw = sc.next();
			
			System.out.println("회원 이름 : ");
			String  memberName = sc.next();
			
			System.out.println("생년월일(ex) 19900101");
			String  memberBirth = sc.next();
			
			System.out.println("이메일 주소를 입력해 주세요. : ");
			String  email = sc.next();
			
			System.out.println("연락처 정보를 입력해 주세요. : ");
			String phone = sc.next();
			
			// member에 회원 정보 셋팅
			member.setMemberId(memberId);
			member.setMemberPw(memberPw);
			member.setMemberName(memberName);
			member.setMemberBirth(memberBirth);
			member.setMemberEmail(email);
			member.setMemberPhone(phone);
			
			int chk = 0;
			chk = mDAO.insertMember(member);
			
			if(chk > 0) {
				System.out.println("등록되었습니다.");
			} else {
				System.out.println("등록에 실패하였습니다.");
			}
			
		}
		
		// 2. 회원 정보 수정
		public void updateMember() {
			System.out.println("회원명을 입력하세요>>>>>>>");
			
			sc.nextLine();
			String name = sc.nextLine();
			
			List<HashMap<String, Object>> memberList = new ArrayList();
			// 이름을 서치해서 나오는 함수 입력 
			memberList = mDAO.printSearchMember(name);
			System.out.println("idx번호\t회원ID\t회원명\t생년월일\t연락처\temail주소");
			// Imp의 대문자 소문자와 맞아야 한다.
			for (int i = 0; i < memberList.size(); i++) {
				System.out.print(memberList.get(i).get("member_idx")+"\t");
				System.out.print(memberList.get(i).get("member_id")+"\t");
				System.out.print(memberList.get(i).get("member_name")+"\t");
				System.out.print(memberList.get(i).get("member_birth")+"\t");
				System.out.print(memberList.get(i).get("member_phone")+"\t");
				System.out.println(memberList.get(i).get("member_email")+"\t");
			}
			System.out.println("수정할 회원명의 순번을 입력하세요>>>>");
			// 몇번째 순번 입력받을지
			int num = sc.nextInt();
			// pk값인 member_id값으로 가져온다.
			// 인덱스번호를 맞추기위해 -1로 맞춘다.
			// <String, Object>>로 객체로 받았기 떄문에 타입변환을 해야한다.
			int memberIDX = Integer.parseInt(memberList.get(num-1).get("member_idx").toString());
			System.out.println("변경할 회원명을 입력하세요>>>>>");
			sc.nextLine();
			String updateName = sc.nextLine();
			
			// DB에서 수정
			int resultChk = 0;
			resultChk = mDAO.updateMember(memberIDX, updateName);
			if(resultChk >0 ) {
				System.out.println("회원정보가 수정되었습니다.");
			} else {
				System.out.println("회원정보수정에 실패하였습니다.");
			}
		}
		
		// 3. 회원정보 삭제
		public void deleteMember() {
System.out.println("회원명을 입력하세요>>>>>>>");
			
			sc.nextLine();
			String name = sc.nextLine();
			
			List<HashMap<String, Object>> memberList = new ArrayList();
			// 이름을 서치해서 나오는 함수 입력 
			memberList = mDAO.printSearchMember(name);
			System.out.println("idx번호\t회원ID\t회원명\t생년월일\t연락처\temail주소");
			// Imp의 대문자 소문자와 맞아야 한다.
			for (int i = 0; i < memberList.size(); i++) {
				System.out.print(memberList.get(i).get("member_idx")+"\t");
				System.out.print(memberList.get(i).get("member_id")+"\t");
				System.out.print(memberList.get(i).get("member_name")+"\t");
				System.out.print(memberList.get(i).get("member_birth")+"\t");
				System.out.print(memberList.get(i).get("member_phone")+"\t");
				System.out.println(memberList.get(i).get("member_email")+"\t");
			}
			System.out.println("수정할 회원명의 순번을 입력하세요>>>>");
			// 몇번째 순번 입력받을지
						int num = sc.nextInt();
						// pk값인 member_id값으로 가져온다.
						// 인덱스번호를 맞추기위해 -1로 맞춘다.
						// <String, Object>>로 객체로 받았기 떄문에 타입변환을 해야한다.
						int memberIDX = Integer.parseInt(memberList.get(num-1).get("member_idx").toString());
						System.out.println("변경할 회원명을 입력하세요>>>>>");
						sc.nextLine();
						String updateName = sc.nextLine();
			
			// DB에서 삭제
			int resultChk = 0;
			resultChk = mDAO.deleteMember(memberIDX, updateName); // 할당받은 변수넣어준다. DAO에서는 다른 변수명으로해도 무방
			if(resultChk >0 ) {
				System.out.println("회원정보가 삭제되었습니다.");
			} else {
				System.out.println("회원정보 삭제에 실패하였습니다.");
			}
		
		}
		
		// 4. 회원정보 출력 (이름)
		public void printSearchMember() {
			List<HashMap<String, Object>> memberList = new ArrayList();
			System.out.println("검색할 회원명을 입력하세요>>>>>>>");
			sc.nextLine();
			String name = sc.nextLine();
			
			memberList = mDAO.printSearchMember(name);
			System.out.println("idx번호\t회원ID\t회원명\t생년월일\t연락처\temail주소");
			for (int i = 0; i < memberList.size(); i++) {
				System.out.print(memberList.get(i).get("member_idx")+"\t");
				System.out.print(memberList.get(i).get("member_id")+"\t");
				System.out.print(memberList.get(i).get("member_name")+"\t");
				System.out.print(memberList.get(i).get("member_birth")+"\t");
				System.out.print(memberList.get(i).get("member_phone")+"\t");
				System.out.println(memberList.get(i).get("member_email")+"\t");
			}
			
		}
		
		// 5. 회원 전체 정보 출력
		public void printAllMembers() {
			List<HashMap<String, Object>> memberList = new ArrayList();
			memberList = mDAO.printAllMembers();
			System.out.println("idx번호\t회원ID\t회원명\t생년월일\t연락처\temail주소");
			for (int i = 0; i < memberList.size(); i++) {
				System.out.print(memberList.get(i).get("member_idx")+"\t");
				System.out.print(memberList.get(i).get("member_id")+"\t");
				System.out.print(memberList.get(i).get("member_name")+"\t");
				System.out.print(memberList.get(i).get("member_birth")+"\t");
				System.out.print(memberList.get(i).get("member_phone")+"\t");
				System.out.println(memberList.get(i).get("member_email")+"\t");
			}
			
		}
}
