package fastdfs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 类说明:上传文件的载体
 *
 * @author 曾鹏
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FastDFSFile {

    /** 文件名 */
    private String name;
    /** 内容 */
    private byte[] content;
    /** 扩展名 */
    private String ext;
    /** md5串 */
    private String md5;
    /** 拥有者 */
    private String author;

    public FastDFSFile(String name, byte[] content, String ext, String height,
                       String width, String author) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;

    }

}
