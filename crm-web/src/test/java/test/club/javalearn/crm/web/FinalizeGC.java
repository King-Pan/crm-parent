package test.club.javalearn.crm.web;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-03-05
 **/
public class FinalizeGC {

    public static FinalizeGC FINALIZE_GE = null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("执行了finalize 方法");
        FinalizeGC.FINALIZE_GE = this;
    }

    public void isAlive() {
        System.out.println("yes,i am still alive:)");
    }

    public static void main(String[] args) throws Throwable {
        FINALIZE_GE = new FinalizeGC();
        // 对象第一次成功拯救自己
        FINALIZE_GE = null;
        System.gc();
        // 因为finalize方法优先级很低,所以暂停0.5秒以等待它
        Thread.sleep(500);
        if (FINALIZE_GE != null) {
            FINALIZE_GE.isAlive();
        } else {
            System.out.println("no,i am dead:(");
        }
        // 下面这段代码与上面的完全相同,但是这次自救却失败了
        FINALIZE_GE = null;
        System.gc();
        // 因为finalize方法优先级很低,所以暂停0.5秒以等待它
        Thread.sleep(500);
        if (FINALIZE_GE != null) {
            FINALIZE_GE.isAlive();
        } else {
            System.out.println("no,i am dead:(");
        }

    }
}
