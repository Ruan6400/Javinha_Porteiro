package com.tavolaquad.porteiro.Utilitarios;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Component
public class JwtFiltro implements Filter{
    

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,ServletException{
        HttpServletRequest req = (HttpServletRequest) request;

        String path = req.getRequestURI();
        System.out.println("Rota acessada: " + path);

        if(path.startsWith("/protected")){
            System.out.println("Rota protegida");
            String authHeader = req.getHeader("Authorization");

            if(authHeader != null && authHeader.startsWith("Bearer ")){
                String token = authHeader.replace("Bearer ","");

                try{
                    JwtUtil.validateToken(token);

                    //extraindo as informações do token
                    String user = JwtUtil.getTokenUser(token);
                    String email = JwtUtil.getTokenEmail(token);


                    //Guardando as informações do token num objeto de informações do usuário
                    CustomUserDetails userDetails = new CustomUserDetails(user,email);

                    //Criando um objeto de autenticação com as credenciais do usuário
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                    //Definindo que o objeto de autenticação sera usado para validações futuras
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                    //Salvando o objeto de autenticação no servidor para validações futuras
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }catch(Exception e){
                    System.out.println("Token inválido");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token inválido");
                    return;
                }
            }else{
                System.out.println(path);
                System.out.println("Token ausente");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
