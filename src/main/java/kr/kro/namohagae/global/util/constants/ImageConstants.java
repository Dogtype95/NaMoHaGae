package kr.kro.namohagae.global.util.constants;

public class ImageConstants {

    private static final String ROOT_URL;         // mapper 파일에 같이 붙이세요
    private static final String ROOT_DIRECTORY;   // 파일 저장할때 쓰세요
    private static final String ROOT_PATH;        // ajax에서 URL 체크 할때 씁니다

    static {    // static 초기화
        // 시스템 체크용 -> 배포할때는 linux, 그 외는 window나 mac
        String os =  System.getProperty("os.name").toLowerCase();
        // 시스템이 linux일 때 도메인 및 이미지 저장 폴더 설정, 그 외는 localhost, 프로젝트 파일 폴더 설정
        if(os.contains("linux")){
            ROOT_URL="http://namohagae.kro.kr/api/v1/image/";
            ROOT_DIRECTORY = "/home/jhp/image/";
        }else{
            ROOT_URL="http://localhost:8081/api/v1/image/";
            ROOT_DIRECTORY =System.getProperty("user.dir")+"/./src/main/resources/static/image/";
        }
        ROOT_PATH="/api/v1/image/";

        // 설정한 ROOT 값들로 각각 매핑 작업
        IMAGE_EMBEDED_URL=ROOT_URL+"embeded?name=";
        IMAGE_EMBEDED_DIRECTORY=ROOT_DIRECTORY+"embeded";
        IMAGE_EMBEDED_PATH=ROOT_PATH+"embeded";

        IMAGE_BOARD_URL=ROOT_URL+"board?name=";
        IMAGE_BOARD_DIRECTORY=ROOT_DIRECTORY+"board";
        IMAGE_BOARD_PATH=ROOT_PATH+"board";

        IMAGE_PRODUCT_URL=ROOT_URL+"product?name=";
        IMAGE_PRODUCT_DIRECTORY=ROOT_DIRECTORY+"product";
        IMAGE_PRODUCT_PATH=ROOT_PATH+"product";

        IMAGE_PROFILE_URL=ROOT_URL+"profile?name=";
        IMAGE_PROFILE_DIRECTORY=ROOT_DIRECTORY+"profile";
        IMAGE_PROFILE_PATH=ROOT_PATH+"profile";

        IMAGE_TEMP_URL=ROOT_URL+"temp?name=";
        IMAGE_TEMP_DIRECTORY=ROOT_DIRECTORY+"temp";
        IMAGE_TEMP_PATH=ROOT_PATH+"temp";

        IMAGE_CHAT_URL=ROOT_URL+"chat?name=";
        IMAGE_CHAT_DIRECTORY=ROOT_DIRECTORY+"chat";
        IMAGE_CHAT_PATH=ROOT_PATH+"chat";

        IMAGE_DOG_URL=ROOT_URL+"dog?name=";
        IMAGE_DOG_DIRECTORY=ROOT_DIRECTORY+"dog";
        IMAGE_DOG_PATH=ROOT_PATH+"dog";
    }
    public static final String IMAGE_EMBEDED_DIRECTORY;
    public static final String IMAGE_EMBEDED_PATH;
    public static final String IMAGE_EMBEDED_URL;
    public static final String IMAGE_BOARD_DIRECTORY;
    public static final String IMAGE_BOARD_PATH;
    public static final String IMAGE_BOARD_URL;
    public static final String IMAGE_PRODUCT_DIRECTORY;
    public static final String IMAGE_PRODUCT_PATH;
    public static final String IMAGE_PRODUCT_URL;
    public static final String IMAGE_PROFILE_DIRECTORY;
    public static final String IMAGE_PROFILE_PATH;
    public static final String IMAGE_PROFILE_URL;
    public static final String IMAGE_TEMP_DIRECTORY;
    public static final String IMAGE_TEMP_PATH;
    public static final String IMAGE_TEMP_URL;
    public static final String IMAGE_CHAT_DIRECTORY;
    public static final String IMAGE_CHAT_PATH;
    public static final String IMAGE_CHAT_URL;
    public static final String IMAGE_DOG_DIRECTORY;
    public static final String IMAGE_DOG_PATH;
    public static final String IMAGE_DOG_URL;

}
