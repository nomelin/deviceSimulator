package top.nomelin.model;

public class Result {
    private String code;
    private String msg;
    private Object data;

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    /**
     * 成功结果
     *
     * @return 成功结果
     */
    public static Result success() {
        return new Result("200", "success");
    }

    /**
     * 成功结果
     *
     * @param data 数据
     * @return 成功结果
     */
    public static Result success(Object data) {
        return new Result("200", "success", data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
