package com.bjcre.resource;

import com.bjcre.bo.HouseInfoBo;
import com.bjcre.params.HouseAddParam;
import com.bjcre.params.HouseQueryParam;
import com.bjcre.server.web.result.BaseResult;
import com.bjcre.server.web.result.CountDataResult;
import com.bjcre.server.web.result.DataResult;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Controller
//@RequestMapping("images")
public class ImageResouce {


    @RequestMapping(value = "images",method = RequestMethod.POST)
    @ResponseBody
    //btnFile对应页面的name属性
    public DataResult add(@RequestParam MultipartFile[] btnFile, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String UPLOAD_PATH= request.getServletContext().getRealPath("/upload/");

            //文件类型:btnFile[0].getContentType()
            //文件名称:btnFile[0].getName()
            if(btnFile[0].getSize()>Integer.MAX_VALUE){//文件长度
//                OutputUtil.jsonArrOutPut(response, JSONArray.fromObject("上传文件过大!"));
            }
            InputStream is = btnFile[0].getInputStream();//多文件也适用,我这里就一个文件
            //String fileName = request.getParameter("fileName");
            String guid = request.getParameter("guid");
            byte[] b = new byte[(int)btnFile[0].getSize()];
            int read = 0;
            int i = 0;
            while((read=is.read())!=-1){
                b[i] = (byte) read;
                i++;
            }
            is.close();
            OutputStream os = new FileOutputStream(new File(UPLOAD_PATH+"/"+guid+"."+btnFile[0].getOriginalFilename()));//文件原名,如a.txt
            os.write(b);
            os.flush();
            os.close();
//            OutputUtil.jsonOutPut(response, null);

        DataResult result = new DataResult();


        return result;
    }

    @RequestMapping(value = "images/{guid}",method = RequestMethod.DELETE)
    @ResponseBody
    public BaseResult filedelete(@PathVariable String guid,HttpServletRequest request, HttpServletResponse response){
        String UPLOAD_PATH= request.getServletContext().getRealPath("/upload/");
        try{
//            String guid = request.getParameter("guid");
            String fileName = request.getParameter("fileName");
            File file = new File(UPLOAD_PATH+"/"+guid+"."+fileName);
            boolean isDeleted = file.delete();
            if(!isDeleted){
//                OutputUtil.errorOutPut(response, "文件删除失败");
            }
//            OutputUtil.jsonArrOutPut(response, null);
        }catch (Exception e) {
//            OutputUtil.errorOutPut(response, "系统异常");
        }

        BaseResult result = new BaseResult();


        return result;
    }

}

