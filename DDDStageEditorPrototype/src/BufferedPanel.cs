using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace DDDMapEditorPrototype
{
    class BufferedPanel : Panel
    {
        public BufferedPanel()
        {
            SetStyle(ControlStyles.UserPaint
                | ControlStyles.AllPaintingInWmPaint
                | ControlStyles.OptimizedDoubleBuffer, true);
            UpdateStyles();
        }
    }
}
