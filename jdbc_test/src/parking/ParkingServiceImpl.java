package parking;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ParkingServiceImpl implements ParkingService{
	
	public static ParkingDAO parkingDAO = new ParkingDAO();
	public static Scanner sc = new Scanner(System.in);
	@Override
	public void startProgram() {
		
			while (true) {

				int menu = printMenu();

				switch(menu) {

				case 1 :
					inParking();
					break;

				case 2 :
					outParking();
					break;

				case 3 :
					System.out.println("시스템 종료 !!");
					System.exit(0);

					break;

				default  :
					System.out.println("1~3 사이의 수를 선택해 주세요.");
					break;
				}
			}
	}
	
	public int printMenu() {
		System.out.println("[ 주차장 현황 ]");
		System.out.println();

		List<HashMap<String, Object>> parkingList = parkingDAO.selectParkingSpace();
		for(int i = 0; i<parkingList.size(); i++) {
			int parkingLocationY = Integer.parseInt(parkingList.get(i).get("parkingY").toString());
			String parkingYn = parkingList.get(i).get("parkingYn").toString();
			int parkingNumber = Integer.parseInt(parkingList.get(i).get("parkingNumber").toString());
			
			// Y가 1이면 줄바꿈
			if(parkingLocationY == 1) {
				System.out.println("");
			}
			
			// 10이하일때 한자리수 탭 맞추기 위해
			if("Y".equals(parkingYn)) {
				if(parkingNumber > 10) {
					System.out.print("■ " + parkingNumber+ " : " + parkingList.get(i).get("parkingYn") + "\t\t");
				}else {
					System.out.print("■ " + parkingNumber+ " : " + parkingList.get(i).get("parkingYn") + "\t\t\t");
				}
			}else {
				if(parkingNumber > 10) {
					System.out.print("□ " + parkingNumber+ " : " + parkingList.get(i).get("parkingYn") + "\t\t");
				}else {
					System.out.print("□ " + parkingNumber+ " : " + parkingList.get(i).get("parkingYn") + "\t\t\t");
				}
				
			}
			
		}

		System.out.println();
		System.out.println("원하는 메뉴를 선택해 주세요.");
		System.out.println("1) 주차	2) 출차 	3) 종료 ");
		int menu = sc.nextInt();

		return menu;
	}
	
	public void inParking() {
		System.out.println("주차하고 싶은 위치를 선택해 주세요");
		System.out.println("(예시) 주차 공간 선택 - 1");

		int location = sc.nextInt();
		
		
		HashMap<String, Object> parkingMap = new HashMap<String, Object>();
		parkingMap = parkingDAO.selectParkingInfo(location);
		
		// valueOf : int형을 문자형으로
		String locationStr = String.valueOf(location);

		// 주차 위치 체크
		if (parkingMap.get("parkingNumber") == null) {
			System.out.println("주차위치를 잘못 입력하셨습니다.");
			System.out.println("처음부터 다시 시작하세요.");
			return; // 처음의 화면으로 돌아가게
		} else {
			if ("Y".equals(parkingMap.get("parkingYn").toString())) {
				System.out.println("이미 다른 차량이 추자되어 있습니다.");
				System.out.println("처음부터 다시 시작하세요.");
				return;
			} else {
				//정상 주차
				System.out.print("주차 차량의 번호를 입력해 주세요. (예: 20가1234) : ");
				String carNum = sc.next();
				
				System.out.print("차량 번호가 " + carNum + " 맞습니까? (y / n)");
				String confirm = sc.next();
				
				if ("y".equals(confirm)) {
					int resultChk = 0;
					resultChk = parkingDAO.insertParking(location,carNum);
					parkingDAO.insertParkingHistory(location, carNum, "I");
					
					if (resultChk > 0) {
						System.out.println("주차가 완료되었습니다 !!");						
						
					} else {
						System.out.println("주차실패");
						
					}
					
				} else {
					System.out.println("처음부터 다시 시작하세요.");
					
				}
			}
			
		}
		
		
		
	}

	public void outParking() {

		System.out.print("출차 위치를 입력해 주세요. : ");
		int location = sc.nextInt();
		int resultChk = 0;
		HashMap<String, Object> parkingMap = new HashMap<String, Object>();
		parkingMap = parkingDAO.selectParkingInfo(location);
		
		if (parkingMap.get("parkingCarNumber") == null ) {
			System.out.println("주차된 차량이 없습니다 !! 안녕히 가세요.");
			return;
		} else {
		
			parkingDAO.insertParkingHistory(location, parkingMap.get("parkingCarNumber").toString(), "O");
			//출차 이력 등록 End
			
			resultChk = parkingDAO.deleteParking(location);
		
		
		
			// 비교 대상의 데이터 타입이 boolean(true 혹은 false)일 경우,
			// if (flag == true) 구문 ==> if (flag)와 동일하게 사용 가능
			// if (flag == false) 구문 ==> if (!flag)와 동일하게 사용 가능
			
			if (resultChk > 0) {
				System.out.println("출차 완료!! 안녕히 가세요.");
			} else {
				System.out.println("주차된 차량이 없습니다 !! 안녕히 가세요.");
			}
	
			}
		}

}
