// middleware.ts
import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

export const config = {
    matcher: ['/((?!_next/static|_next/image|favicon.ico|assets|fonts|images|api).*)'], // 감시할 경로 패턴
}

export function middleware(req: NextRequest) {
  const { pathname } = req.nextUrl;

  // 이미 인증되어 있는데, 로그인 하려는 경우. main 페이지로 튕김
  if(pathname.startsWith('/login') && req.cookies.get('refresh_token')?.value){
    return NextResponse.redirect(new URL('/', req.url));
  }

  // 인증 안된 상태로 로그인 하려는 경우. 허용.
  if(pathname.startsWith('/login') && !req.cookies.get('refresh_token')?.value){
    return NextResponse.next();
  }

  if(pathname=="/" && !req.cookies.get('refresh_token')?.value ){
    return NextResponse.redirect(new URL('/login', req.url));
  }

  // 쿠키에서 refresh_token 추출.
  const token = req.cookies.get('refresh_token')?.value;

  if(pathname)

  // 인증이 필요하지만, 쿠키가 없을 경우.
  if (!token) {
    return NextResponse.redirect(new URL('/login', req.url));
  }

  try {
    return NextResponse.next();
  } catch (err) {
    console.error('[Middleware: JWT Error]', err);
    return NextResponse.redirect(new URL('/login', req.url));
  }
}
