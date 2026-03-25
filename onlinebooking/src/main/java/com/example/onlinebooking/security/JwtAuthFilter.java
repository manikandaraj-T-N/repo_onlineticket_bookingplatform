// package com.example.onlinebooking.security;
// import java.io.IOException;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JwtAuthFilter extends OncePerRequestFilter {

//     private final JwtUtil jwtUtil;
//     private final UserDetailsService userDetailsService;

//     public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//         this.jwtUtil = jwtUtil;
//         this.userDetailsService = userDetailsService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain filterChain)
//             throws ServletException, IOException {

//         String jwt = null;

//         // Read JWT from cookie
//         if (request.getCookies() != null) {
//             for (Cookie cookie : request.getCookies()) {
//                 if (cookie.getName().equals("jwt")) {
//                     jwt = cookie.getValue();
//                 }
//             }
//         }

//         if (jwt != null && jwtUtil.validateToken(jwt)) {

//             String email = jwtUtil.extractUsername(jwt);

//                 // Extract name from JWT and pass to controllers
//              String name = jwtUtil.extractAllClaims(jwt).get("name", String.class);
//             request.setAttribute("username", name);

//             // If user not already authenticated
//             if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

//                 UserDetails userDetails = userDetailsService.loadUserByUsername(email);

//                 UsernamePasswordAuthenticationToken authToken =
//                         new UsernamePasswordAuthenticationToken(
//                                 userDetails,
//                                 null,
//                                 userDetails.getAuthorities()
//                         );

//                 authToken.setDetails(
//                         new WebAuthenticationDetailsSource().buildDetails(request)
//                 );

//                 SecurityContextHolder.getContext().setAuthentication(authToken);
//             }
//         }

//         filterChain.doFilter(request, response);
//     }
// }

