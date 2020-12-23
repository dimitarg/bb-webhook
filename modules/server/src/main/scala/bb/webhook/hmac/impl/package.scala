package bb.webhook.hmac

import cats.effect.Sync

package object impl {
  def signatureVerifier[F[_]: Sync]: SignatureVerifier[F] = new SignatureVerifier[F] {
    override def verify(key: SecretKey)(message: String): F[Boolean] = {
      Sync[F].raiseError(new RuntimeException("not implemented"))
    }
  }
}
