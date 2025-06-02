import type { Metadata } from "next";
import StyledComponentsRegistry from '../lib/registry'
import {Toaster} from "sonner";
import "./globals.css";

export const metadata: Metadata = {
  title: "Kakao Oauth Examples",
  description: "카카오 로그인 예제",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html>
      <body>
        <StyledComponentsRegistry>
          {children}
        </StyledComponentsRegistry>
        <Toaster position="top-right" richColors closeButton />
      </body>
    </html>
  )
}
