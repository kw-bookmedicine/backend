package kr.KWGraduate.BookPharmacy.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.jwt.JWTUtil;
import kr.KWGraduate.BookPharmacy.service.ClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    //나중에 Detail에 dto로 변경해도 될듯함
    private final ClientDetailsService clientDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String authorization = jwtUtil.resolveToken(request);

            System.out.println("authorization now");
            String token = authorization.split(" ")[1];

            if(!jwtUtil.validateToken(token)){
                System.out.println("not valid");
                filterChain.doFilter(request, response);
                return;
            }

            Authentication authToken = jwtUtil.getAuthentication(token);
            //세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);


        }catch (Exception e){
            request.setAttribute("exception",e);
        }
        finally {
            filterChain.doFilter(request, response);
        }
    }
}
