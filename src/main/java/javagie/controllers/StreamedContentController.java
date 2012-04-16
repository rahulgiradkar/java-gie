package javagie.controllers;

import java.util.ArrayList;
import java.util.List;
import javagie.dto.StreamedContentDto;
import javagie.util.ConstantesUtil;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author eduardo
 */
@Controller
@RequestMapping("/streamedcontent")
public class StreamedContentController {
    
    private List<StreamedContentDto> getStreamedContentList(HttpSession session) {
        return (List<StreamedContentDto>) session.getAttribute(ConstantesUtil.SESSION_KEY_STREAMED_CONTENT_LIST);
    }
    
    public StreamedContentDto getStreamedContent(HttpSession session, Integer index) {
        List<StreamedContentDto> list = getStreamedContentList(session);
        if(list != null && list.get(index) != null) {
            return list.get(index);
        }else {
            return null;
        }
    }
    
    public void setStreamedContent(HttpSession session, StreamedContentDto content) {
        List<StreamedContentDto> list = new ArrayList<StreamedContentDto>();
        list.add(content);
        setStreamedContentList(session, list);
    }
    
    public void setStreamedContentList(HttpSession session, List<StreamedContentDto> streamedContentList) {
        session.setAttribute(ConstantesUtil.SESSION_KEY_STREAMED_CONTENT_LIST, streamedContentList);
    }
    
    public String urlStreamedContent(Integer index) {
        return "/streamedcontent/"+index;
    }
    
    public String urlStreamedContent() {
        return urlStreamedContent(0);
    }
    
    public void limpiarSession(HttpSession httpSession) {
        httpSession.removeAttribute(ConstantesUtil.SESSION_KEY_STREAMED_CONTENT_LIST);
    }
    
    //MAPIADOS A URL
    @RequestMapping("/{index}")
    protected ResponseEntity<byte[]> imagenSubida(@PathVariable("index")Integer index, HttpSession session) throws Exception{
        final HttpHeaders headers = new HttpHeaders();
        StreamedContentDto sc = getStreamedContent(session, index);
        if(sc == null) {
            return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        }
        headers.setContentType(MediaType.parseMediaType(sc.getContentType()));
        return new ResponseEntity<byte[]>(sc.getByteArray(), headers, HttpStatus.OK);
    }
}
