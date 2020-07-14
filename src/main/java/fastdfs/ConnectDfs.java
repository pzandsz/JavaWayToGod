//package fastdfs;
//
//import org.csource.common.NameValuePair;
//import org.csource.fastdfs.ClientGlobal;
//import org.csource.fastdfs.StorageClient;
//import org.csource.fastdfs.TrackerClient;
//import org.csource.fastdfs.TrackerServer;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//
///**
// * 类说明:服务端使用fastDfs
// *
// * @author 曾鹏
// */
//public class ConnectDfs {
//    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ConnectDfs.class);
//
//    static {
//        try {
//            //加载配置信息
//            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();;
//            ClientGlobal.init(filePath);
//        } catch (Exception e) {
//            logger.error("FastDFS Client Init Fail!",e);
//        }
//    }
//
//    /**
//     * 上传文件
//     * @param file
//     * @return
//     */
//    public static String[] upload(FastDFSFile file) {
//        //打印文件信息
//        logger.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);
//
//        NameValuePair[] meta_list = new NameValuePair[1];
//        meta_list[0] = new NameValuePair("author", file.getAuthor());
//
//        long startTime = System.currentTimeMillis();
//        String[] uploadResults = null;
//        StorageClient storageClient=null;
//        try {
//            storageClient = getTrackerClient();
//            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
//        } catch (IOException e) {
//            logger.error("IO Exception when uploadind the file:" + file.getName(), e);
//        } catch (Exception e) {
//            logger.error("Non IO Exception when uploadind the file:" + file.getName(), e);
//        }
//        logger.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");
//
//        //上传异常
//        if (uploadResults == null && storageClient!=null) {
//            logger.error("upload file fail, error code:" + storageClient.getErrorCode());
//        }
//        String groupName = uploadResults[0];
//        String remoteFileName = uploadResults[1];
//
//        logger.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
//        return uploadResults;
//    }
//
//
////    public static String getTrackerUrl() throws IOException {
////        return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port()+"/";
////    }
//
//    private static StorageClient getTrackerClient() throws IOException {
//        TrackerServer trackerServer = getTrackerServer();
//        StorageClient storageClient = new StorageClient(trackerServer, null);
//        return  storageClient;
//    }
//
//    private static TrackerServer getTrackerServer() throws IOException {
//        TrackerClient trackerClient = new TrackerClient();
//        TrackerServer trackerServer = trackerClient.getConnection();
//        return  trackerServer;
//    }
//}
