package demo.bitwiseDemo;

/**
 * 类说明:使用位运算进行权限控制
 * insert
 * query
 * modify
 * delete
 *
 * 下面代码实现权限的方法是在对应的位上的值是否为0
 * 例如:flag= 0 0 0 0 0 1 0 1:
 *      因为在第8位和第6位上的值为1，所以允许查询和更新
 *      因为第7位和第5位为0，所以表示禁止插入和删除
 * @author 曾鹏
 */
public class Permission {

    /**
     * 表示允许查询
     */
    private static final int ALLOW_SELECT=1<<0;
    /**
     * 表示允许插入
     */
    private static final int ALLOW_INSERT=1<<1;
    /**
     * 表示允许更新
     */
    private static final int ALLOW_UPDATE=1<<2;
    /**
     * 表示允许删除
     */
    private static final int ALLOW_DELETE=1<<3;

    /**
     * 当前权限状态
     */
    private int flag;

    /**
     * 设置权限
     * @param permission
     */
    public void setPermission(int permission){
        /**
         * 为什么可以直接进行或运算???
         */
        flag=permission;
    }


    /**
     * 增加权限，可以为一项或者多项
     * @param permission
     */
    public void addPermission(int permission){
        /**
         * int有32位，仅使用8位用于理解
         * 为什么可以直接进行或运算???
         * 例如flag=0(00000000)，表示最初不允许任何操作
         * 当允许查询时，传入00000001,将两者进行或运算得到结果:00000001,此时第八位为1，表示允许查询
         * 如果再允许添加，传入00000010，和flag进行|运算后得到:00000011,第七为也为1，表示允许添加
         */
        flag=flag|permission;
    }

    /**
     * 删除权限，可以为一项或者多项
     * @param permission
     */
    public void disablePermission(int permission){
        /**
         * 为什么进行&~运算就可以了???
         */
        flag=flag&~permission;
    }

    /**
     * 是否拥有某些权限
     *
     * @param permission
     * @return
     */
    public boolean isAllow(int permission){
        //flag和
        return (flag&permission)==permission;
    }

    /**
     * 是否不拥有某些权限
     * @param permission
     * @return
     */
    public boolean isNotAllow(int permission){
        return (flag&permission)==0;
    }


    public static void main(String[] args) {
        int flag=15;
        Permission permission=new Permission();
        permission.setPermission(flag);

        /**
         * 禁止了删除权限和添加权限
         */
        permission.disablePermission(ALLOW_DELETE|ALLOW_INSERT);

        System.out.println("ALLOW_SELECT="+permission.isAllow(ALLOW_SELECT));
        System.out.println("ALLOW_INSERT="+permission.isAllow(ALLOW_INSERT));
        System.out.println("ALLOW_UPDATE="+permission.isAllow(ALLOW_UPDATE));
        System.out.println("ALLOW_DELETE="+permission.isAllow(ALLOW_DELETE));


    }
}
