//package fastdfs;
//
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * 类说明:
// *
// * @author 曾鹏
// */
//@Controller
//@RequestMapping(("/test"))
//public class TestController {
//
//    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TestController.class);
//
//    @PostMapping("/upload")
//    public String singleFileUpload(@RequestParam("file") MultipartFile file,//用@RequestParam获取传递过来的文件
//                                   RedirectAttributes redirectAttributes) {
//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "redirect:uploadStatus";
//        }
//        try {
//            // 获取文件保存
//            String path=saveFile(file);
//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
//            redirectAttributes.addFlashAttribute("path",
//                    "file path url '" + path + "'");
//        } catch (Exception e) {
//            logger.error("upload file failed",e);
//        }
//        return "redirect:/uploadStatus";
//    }
//
//    @GetMapping("/uploadStatus")
//    public String uploadStatus() {
//        return "uploadStatus";
//    }
//
//    /**
//     * @param multipartFile
//     * @return
//     * @throws IOException
//     */
//    public String saveFile(MultipartFile multipartFile) throws IOException {
//        String[] fileAbsolutePath={};
//        String fileName=multipartFile.getOriginalFilename();
//        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
//        byte[] file_buff = null;
//        InputStream inputStream=multipartFile.getInputStream();
//        if(inputStream!=null){
//            int len1 = inputStream.available();
//            file_buff = new byte[len1];
//            inputStream.read(file_buff);
//        }
//        inputStream.close();
//        //构造一个FastDFSFile对象
//        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
//        try {
//            //上传到fastdfs
//            fileAbsolutePath = ConnectDfs.upload(file);
//
//        } catch (Exception e) {
//            logger.error("upload file Exception!",e);
//        }
//        if (fileAbsolutePath==null) {
//            logger.error("upload file failed,please upload again!");
//        }
//        //上传成功返回文件path  ConnectDfs.getTrackerUrl()+fileAbsolutePath[0]+
//        String path="/"+fileAbsolutePath[1];
//        return path;
//    }
//
//}
