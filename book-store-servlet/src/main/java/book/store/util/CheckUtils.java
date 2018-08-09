package book.store.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {
    private static final Pattern mobileNumPattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0,1,6-8])|(18[0-9]))\\d{8}$");
    private static final Pattern pswPattern = Pattern.compile("^(.*[0-9]+.*[a-zA-Z]+.*)|(.*[a-zA-Z]+.*[0-9]+.*)$");
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_REAL_NAME_REGEX = Pattern.compile("^[\u4E00-\u9FA5]{2,16}$");


    /**
     * 匹配手机号是否合法
     *
     * @param mobileNo
     * @return
     */
    public static boolean checkMobileNo(String mobileNo) {
        if (StringUtils.isBlank(mobileNo)) {
            return false;
        }
        Matcher matcher = mobileNumPattern.matcher(mobileNo);
        return matcher.matches();
    }

    /**
     * 密码是否符合规则
     *
     * @param password
     * @return
     */
    public static boolean checkPsw(String password) {
        Matcher matcher = pswPattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 校验邮箱是否符合规则
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    /**
     * 校验是否是真实姓名是否符合规则
     *
     * @param realName
     * @return
     */
    public static boolean checkRealName(String realName) {
        Matcher matcher = VALID_REAL_NAME_REGEX.matcher(realName);
        return matcher.matches();
    }
}
