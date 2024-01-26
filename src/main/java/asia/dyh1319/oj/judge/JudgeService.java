package asia.dyh1319.oj.judge;

/**
 * 判题服务
 */
public interface JudgeService {
    
    /**
     * 执行判题
     *
     * @param submitId 提交ID
     */
    void doJudge(Long submitId);
}
