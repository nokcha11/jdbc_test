package member;

public class MemberController {

	public static void main(String[] args) {
		
		MemberService memberService = new MemberServiceImpl();
		
		memberService.startProgram();

	}

}
