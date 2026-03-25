import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Typography,
  Stack
} from "@mui/material";
import WarningAmberIcon from "@mui/icons-material/WarningAmber";

export default function ConfirmDialog({
  open,
  title = "Confirmar ação",
  message = "Tem certeza que deseja continuar?",
  confirmText = "Confirmar",
  cancelText = "Cancelar",
  onConfirm,
  onClose,
  loading = false,
  severity = "warning" // "warning" | "error" | "info"
}) {
  const color =
    severity === "error" ? "error" : severity === "info" ? "info" : "warning";

  return (
    <Dialog open={open} onClose={loading ? undefined : onClose} maxWidth="xs" fullWidth>
      <DialogTitle>
        <Stack direction="row" spacing={1.5} alignItems="center">
          <WarningAmberIcon color={color} />
          <Typography variant="h6" fontWeight={800}>
            {title}
          </Typography>
        </Stack>
      </DialogTitle>

      <DialogContent dividers>
        <Typography sx={{ opacity: 0.9 }}>{message}</Typography>
      </DialogContent>

      <DialogActions sx={{ p: 2 }}>
        <Button onClick={onClose} disabled={loading} variant="text">
          {cancelText}
        </Button>

        <Button
          onClick={onConfirm}
          disabled={loading}
          color={color}
          variant="contained"
        >
          {loading ? "Processando..." : confirmText}
        </Button>
      </DialogActions>
    </Dialog>
  );
}