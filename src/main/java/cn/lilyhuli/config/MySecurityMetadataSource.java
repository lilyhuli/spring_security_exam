package cn.lilyhuli.config;

import cn.lilyhuli.bean.MyResourceBean;
import cn.lilyhuli.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    ResourceMapper resourceMapper;
    //本方法返回访问资源所需的角色集合
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //从object中得到需要访问的资源，即网址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //从数据库中得到所有资源，以及对应的角色
        List<MyResourceBean> resourceBeans = resourceMapper.selectAllResource();
        for (MyResourceBean resource : resourceBeans) {
            //首先进行地址匹配
            if (antPathMatcher.match(resource.getUrl(), requestUrl)
                    && resource.getRolesArray().length > 0) {
                return SecurityConfig.createList(resource.getRolesArray());
            }
        }
        //匹配不成功返回一个特殊的ROLE_NONE
        return SecurityConfig.createList("ROLE_NONE");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
