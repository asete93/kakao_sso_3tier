// middleware.ts
import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

export const config = {
    matcher: ['/((?!_next/static|_next/image|favicon.ico|assets|fonts|images|api).*)'], // 감시할 경로 패턴
}

export function middleware(req: NextRequest) {
  const { pathname } = req.nextUrl;

  // 이미 인증되어 있는데, 로그인 하려는 경우. main 페이지로 튕김
  if(pathname.startsWith('/login') && req.cookies.get('loginUser')?.value){
    return NextResponse.redirect(new URL('/', req.url));
  }

  // 인증 안된 상태로 로그인 하려는 경우. 허용.
  if(pathname.startsWith('/login') && !req.cookies.get('loginUser')?.value){
    return NextResponse.next();
  }

  // 인증 안된 상태로 회원가입 하려는 경우. 허용.
  if(pathname.startsWith('/signup') && !req.cookies.get('loginUser')?.value){
    return NextResponse.next();
  }

  if(pathname=="/" && !req.cookies.get('loginUser')?.value ){
    return NextResponse.redirect(new URL('/login', req.url));
  }

  // 쿠키에서 loginUser 추출.
  const loginUser = req.cookies.get('loginUser')?.value;

  // 로그인 안한 경우.
  if (!loginUser) {
    return NextResponse.redirect(new URL('/login', req.url));
  }

  try {
    return NextResponse.next();
  } catch (err) {
    console.error('[Middleware: JWT Error]', err);
    return NextResponse.redirect(new URL('/login', req.url));
  }
}
